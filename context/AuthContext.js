'use client';

import { createContext, useState, useEffect } from 'react';
const jwtDecode = require('jwt-decode').default;
import { getToken, setToken, clearToken } from '../lib/auth';

export const AuthContext = createContext();

export default function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [token, setAuthToken] = useState(null); // ✅ Add token state

  useEffect(() => {
    const storedToken = getToken();
    if (storedToken) {
      try {
        const decoded = jwtDecode(storedToken);
        setUser({ username: decoded.userName || decoded.sub || 'Unknown' });
        setAuthToken(storedToken); // ✅ set token from storage
      } catch {
        clearToken();
        setUser(null);
        setAuthToken(null);
      }
    }
  }, []);

  const login = (newToken) => {
    setToken(newToken);        // save to localStorage
    setAuthToken(newToken);    // ✅ update token state
    try {
      const decoded = jwtDecode(newToken);
      setUser({ username: decoded.userName || decoded.sub || 'Unknown' });
    } catch {
      setUser(null);
    }
  };

  const logout = () => {
    clearToken();
    setUser(null);
    setAuthToken(null); // ✅ clear token state
  };

  return (
    <AuthContext.Provider value={{ user, token, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

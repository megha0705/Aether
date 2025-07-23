'use client';

import { useState, useContext } from 'react';
import { AuthContext } from '../../../context/AuthContext';
import axios from 'axios';
import { useRouter } from 'next/navigation';

export default function LoginPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const router = useRouter();
  const { login } = useContext(AuthContext);

  const handleLogin = async (e) => {
    e.preventDefault();
    setError('');

    const body = new URLSearchParams();
    body.append('userName', username);
    body.append('password', password);

    try {
      const res = await axios.post('http://localhost:8080/login', body, {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      });

      login(res.data); // Backend returns plain JWT string
      console.log(String(res.data))
      router.push('/dashboard');
    } catch (err) {
      setError(err?.response?.data?.message || 'Login failed.');
    }
  };

  return (
    <div className="container d-flex align-items-center justify-content-center min-vh-100">
      <div className="w-100" style={{ maxWidth: '500px' }}>
        <div className="card bg-dark-custom text-white border border-crimson p-4">
          <h2 className="mb-4 text-center text-crimson">Login to Your Account</h2>

          {error && (
            <div className="alert alert-danger text-center" role="alert">
              {error}
            </div>
          )}

          <form onSubmit={handleLogin}>
            <div className="mb-3">
              <label htmlFor="username" className="form-label text-white">Username</label>
              <input
                type="text"
                className="form-control bg-black text-white border-crimson"
                id="username"
                name="username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
              />
            </div>

            <div className="mb-4">
              <label htmlFor="password" className="form-label text-white">Password</label>
              <input
                type="password"
                className="form-control bg-black text-white border-crimson"
                id="password"
                name="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>

            <button type="submit" className="btn btn-primary w-100">
              Login
            </button>
          </form>

          <div className="mt-3 text-center">
            <p className="text-white">
              Don&apos;t have an account?{' '}
              <a href="/register" className="text-crimson fw-bold" style={{ textDecoration: 'underline' }}>
                Register here
              </a>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}

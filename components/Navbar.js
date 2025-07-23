'use client';

import { useContext, useEffect, useState } from 'react';
import { AuthContext } from '../context/AuthContext';
import axios from 'axios';
import { useRouter } from 'next/navigation';
import Image from 'next/image';

export default function Navbar() {
  const { token, logout } = useContext(AuthContext);
  const [username, setUsername] = useState('');
  const router = useRouter();

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const res = await axios.get('http://localhost:8080/getUserProfile', {
          headers: { Authorization: `Bearer ${token}` },
        });
        setUsername(res.data); // Assuming this returns username or profile info
      } catch (err) {
        console.error('Failed to fetch profile');
      }
    };

    fetchProfile();
  }, [token]);

  const handleLogout = () => {
    logout();
    router.push('/login');
  };

  return (
    <nav className="navbar navbar-dark bg-dark-custom px-4">
      <span className="navbar-brand mb-0 h1 text-crimson">        
        <Image
                src="/aether1.png"         // From public folder
                alt="Aether Chat Logo"
                width={500}               // Adjust width as needed
                height={125}               // Adjust height as needed
                priority                  // Loads image faster
              /></span>
      <div className="dropdown">
        <button
          className="btn btn-outline-light dropdown-toggle"
          data-bs-toggle="dropdown"
          aria-expanded="false"
        >
          {username || 'Profile'}
        </button>
        <ul className="dropdown-menu dropdown-menu-dark">
          <li><a className="dropdown-item" href="/profile">Profile</a></li>
          <li><button className="dropdown-item" onClick={handleLogout}>Logout</button></li>
        </ul>
      </div>
    </nav>
  );
}

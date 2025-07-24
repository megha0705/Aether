'use client';

import { useContext, useEffect, useState, useRef } from 'react';
import { AuthContext } from '../context/AuthContext';
import axios from 'axios';
import { useRouter } from 'next/navigation';
import Image from 'next/image';

export default function Navbar() {
  const { token, logout } = useContext(AuthContext);
  const [profilePic, setProfilePic] = useState('');
  const router = useRouter();
  const fileInputRef = useRef(null);

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const res = await axios.get('http://localhost:8080/getUserProfile', {
          headers: { Authorization: `Bearer ${token}` },
        });
        setProfilePic(res.data); // Assuming it returns a direct image URL
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

  const handleFileChange = async (e) => {
    const file = e.target.files[0];
    if (!file) return;

    const formData = new FormData();
    formData.append('image', file);

    try {
      const res = await axios.post('http://localhost:8080/uploadProfilePic', formData, {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'multipart/form-data',
        },
      });
      setProfilePic(res.data); // New image URL returned
    } catch (err) {
      console.error('Failed to upload profile picture', err);
    }
  };

  return (
    <nav className="navbar navbar-dark bg-dark-custom px-4">
      <span className="navbar-brand mb-0 h1 text-crimson">
        <Image
          src="/aether1.png"
          alt="Aether Chat Logo"
          width={500}
          height={125}
          priority
        />
      </span>
      <div className="dropdown">
        <button
          className="btn btn-outline-light dropdown-toggle d-flex align-items-center gap-2"
          data-bs-toggle="dropdown"
          aria-expanded="false"
        >
          {profilePic ? (
            <Image
              src={profilePic}
              alt="Profile"
              width={32}
              height={32}
              className="rounded-circle"
            />
          ) : (
            <span className="text-white">Profile</span>
          )}
        </button>
        
        <ul
          className="dropdown-menu dropdown-menu-end dropdown-menu-dark"
          aria-labelledby="dropdownMenuButton"
          style={{ minWidth: '200px', right: 0 }}
        >
          <li className="px-3 py-2 text-center">
            <input
              type="file"
              accept="image/*"
              onChange={handleFileChange}
              ref={fileInputRef}
              style={{ display: 'none' }}
            />
            <button
              className="btn btn-sm btn-outline-light w-100"
              onClick={() => fileInputRef.current.click()}
            >
              Upload Picture
            </button>
          </li>
          <li><hr className="dropdown-divider" /></li>
          <li><button className="dropdown-item" onClick={handleLogout}>Logout</button></li>
        </ul>
      </div>
    </nav>
  );
}

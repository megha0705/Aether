'use client';

import { useState, useContext, useEffect } from 'react';
import axios from 'axios';
import { useRouter } from 'next/navigation';
import { AuthContext } from '../../context/AuthContext';

export default function CreateRoomPage() {
  const [roomName, setRoomName] = useState('');
  const [roomId, setRoomId] = useState('');
  const [error, setError] = useState('');
  const router = useRouter();
  const { token } = useContext(AuthContext);

  useEffect(() => {
    if (!token) {
      router.push('/login'); // redirect to login if no token
    }
  }, [token]);

  const handleCreateRoom = async (e) => {
    e.preventDefault();
    setError('');

    if (!token) {
      setError('You must be logged in to create a room.');
      return;
    }

    const body = new URLSearchParams();
    body.append('roomName', roomName);
    body.append('roomId', roomId);

    try {
      await axios.post('http://localhost:8080/createRoom', body, {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
          Authorization: `Bearer ${token}`,
        },
      });

      router.push('/dashboard');
    } catch (err) {
      console.error('Room creation failed:', err);
      setError(err?.response?.data?.message || 'Room creation failed.');
    }
  };

  return (
    <div className="container d-flex align-items-center justify-content-center min-vh-100">
      <div className="w-100" style={{ maxWidth: '500px' }}>
        <div className="card bg-dark-custom text-white border border-crimson p-4">
          <h2 className="mb-4 text-center text-crimson">Create a Room</h2>

          {error && (
            <div className="alert alert-danger text-center" role="alert">
              {error}
            </div>
          )}

          <form onSubmit={handleCreateRoom}>
            <div className="mb-3">
              <label htmlFor="roomName" className="form-label text-white">Room Name</label>
              <input
                type="text"
                className="form-control bg-black text-white border-crimson"
                id="roomName"
                value={roomName}
                onChange={(e) => setRoomName(e.target.value)}
                required
              />
            </div>

            <div className="mb-4">
              <label htmlFor="roomId" className="form-label text-white">Room ID</label>
              <input
                type="text"
                className="form-control bg-black text-white border-crimson"
                id="roomId"
                value={roomId}
                onChange={(e) => setRoomId(e.target.value)}
                required
              />
            </div>

            <button type="submit" className="btn btn-primary w-100">Create</button>
          </form>
        </div>
      </div>
    </div>
  );
}

'use client';

import { useState, useContext } from 'react';
import axios from 'axios';
import { useRouter } from 'next/navigation';
import { AuthContext } from '../../context/AuthContext';

export default function JoinRoomPage() {
  const [roomId, setRoomId] = useState('');
  const [error, setError] = useState('');
  const router = useRouter();
  const { token } = useContext(AuthContext);

  const handleJoinRoom = async (e) => {
    e.preventDefault();
    setError('');

    const body = new URLSearchParams();
    body.append('roomId', roomId);

    try {
      await axios.post('http://localhost:8080/joinRoom', body, {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
          Authorization: `Bearer ${token}`,
        },
      });

      // Redirect to dashboard instead of chat page
      router.push('/dashboard');
    } catch (err) {
      setError('Failed to join room.');
    }
  };

  return (
    <div className="container d-flex align-items-center justify-content-center min-vh-100">
      <div className="w-100" style={{ maxWidth: '500px' }}>
        <div className="card bg-dark-custom text-white border border-crimson p-4">
          <h2 className="mb-4 text-center text-crimson">Join a Room</h2>

          {error && (
            <div className="alert alert-danger text-center" role="alert">
              {error}
            </div>
          )}

          <form onSubmit={handleJoinRoom}>
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

            <button type="submit" className="btn btn-primary w-100">Join</button>
          </form>
        </div>
      </div>
    </div>
  );
}

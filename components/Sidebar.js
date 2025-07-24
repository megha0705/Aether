'use client';

import { useEffect, useState, useContext } from 'react';
import axios from 'axios';
import { AuthContext } from '../context/AuthContext';
import { useRouter } from 'next/navigation';

export default function Sidebar({ onRoomSelect }) {
  const [rooms, setRooms] = useState([]);
  const { token, user } = useContext(AuthContext);
  const router = useRouter();

  useEffect(() => {
    fetchRooms();
  }, [token]);

  const fetchRooms = async () => {
    try {
      const res = await axios.get('http://localhost:8080/getAllRoom', {
        headers: { Authorization: `Bearer ${token}` },
      });

      // ✅ Filter rooms created by current user
      const filtered = res.data.filter((room) => {
        return room.createdBy === user?.username;
      });

      setRooms(filtered);
    } catch (err) {
      console.error('Error fetching rooms:', err);
    }
  };

  const handleCreate = () => router.push('/create-room');
  const handleJoin = () => router.push('/join-room');

  const handleDeleteRoom = async (roomId) => {
    if (!window.confirm('Are you sure you want to delete this room?')) return;

    try {
      await axios.delete(`http://localhost:8080/deleteRoom/${roomId}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setRooms((prev) => prev.filter((room) => room.roomId !== roomId));
    } catch (err) {
      console.error('Error deleting room:', err);
    }
  };

  return (
    <div className="p-3 bg-black text-white h-100" style={{ width: '250px' }}>
      <button className="btn btn-outline-light w-100 mb-2" onClick={handleCreate}>
        + Create Room
      </button>
      <button className="btn btn-outline-light w-100 mb-3" onClick={handleJoin}>
        🔗 Join Room
      </button>

      <h6 className="text-crimson">Your Rooms</h6>
      <ul className="list-group bg-black">
        {rooms.map((room) => (
          <li
            key={room.roomId}
            className="list-group-item bg-dark-custom text-white border-crimson mb-1 d-flex justify-content-between align-items-center"
            style={{ cursor: 'pointer' }}
          >
            <span onClick={() => onRoomSelect(room.roomId)}>{room.roomName}</span>
            <button
              className="btn btn-sm btn-light ms-2"
              onClick={(e) => {
                e.stopPropagation();
                handleDeleteRoom(room.roomId);
              }}
            >
              ❌
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
}

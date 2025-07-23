'use client';

import { useEffect, useState, useContext } from 'react';
import axios from 'axios';
import { AuthContext } from '../context/AuthContext';
import { useRouter } from 'next/navigation';

export default function Sidebar({ onRoomSelect }) {
  const [rooms, setRooms] = useState([]);
  const { token } = useContext(AuthContext);
  const router = useRouter();

  useEffect(() => {
    const fetchRooms = async () => {
      try {
        const res = await axios.get('http://localhost:8080/getAllRoom', {
          headers: { Authorization: `Bearer ${token}` },
        });
        setRooms(res.data); // Filter this if needed
      } catch (err) {
        console.error('Error fetching rooms');
      }
    };
    fetchRooms();
  }, [token]);

  const handleCreate = () => router.push('/create-room');
  const handleJoin = () => router.push('/join-room');

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
            className="list-group-item bg-dark-custom text-white border-crimson mb-1"
            style={{ cursor: 'pointer' }}
            onClick={() => onRoomSelect(room.roomId)}
          >
            {room.roomName}
          </li>
        ))}
      </ul>
    </div>
  );
}

'use client';

import { useEffect, useState } from 'react';
import axios from 'axios';

export default function ParticipantsList({ roomId, token }) {
  const [participants, setParticipants] = useState([]);

  useEffect(() => {
    const fetchParticipants = async () => {
      try {
        const res = await axios.get(`http://localhost:8080/getParticipants/${roomId}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setParticipants(res.data);
      } catch (err) {
        console.error('Failed to fetch participants:', err);
      }
    };

    fetchParticipants();
  }, [roomId, token]);

  return (
    <ul className="list-unstyled">
      {participants.length === 0 ? (
        <li className="text-muted">No participants</li>
      ) : (
        participants.map((user, idx) => (
          <li key={idx} className="mb-2">{user}</li>
        ))
      )}
    </ul>
  );
}

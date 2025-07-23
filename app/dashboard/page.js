'use client';

import { useState, useContext } from 'react';
import { AuthContext } from '../../context/AuthContext';
import Navbar from '../../components/Navbar';
import Sidebar from '../../components/Sidebar';
import ChatBox from '../../components/ChatBox';

export default function DashboardPage() {
  const { token } = useContext(AuthContext);
  const [activeRoom, setActiveRoom] = useState(null);

  return (
    <div className="d-flex flex-column min-vh-100">
      <Navbar />
      <div className="d-flex flex-grow-1">
        <Sidebar onRoomSelect={setActiveRoom} />
        {activeRoom ? (
          <ChatBox roomId={activeRoom} token={token} />
        ) : (
          <div className="flex-grow-1 d-flex justify-content-center align-items-center text-white">
            Select a room to start chatting
          </div>
        )}
      </div>
    </div>
  );
}

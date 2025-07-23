'use client';

import { useEffect, useState, useRef } from 'react';
import io from 'socket.io-client';
import MessageInput from '../../../../components/MessageInput';
import ParticipantsList from '../../../../components/ParticipantsList';
import axios from 'axios';

export default function ChatRoom({ params }) {
  const [messages, setMessages] = useState([]);
  const [typingUser, setTypingUser] = useState(null);
  const [search, setSearch] = useState('');
  const [roomInfo, setRoomInfo] = useState({ name: 'Loading...' });

  const socketRef = useRef(null);

  useEffect(() => {
    // Fetch room info (name, description)
    axios.get(`http://localhost:8080/api/rooms/${params.roomId}`)
      .then(res => setRoomInfo(res.data))
      .catch(() => setRoomInfo({ name: 'Unknown Room' }));

    // Init socket
    socketRef.current = io('http://localhost:8080');
    socketRef.current.emit('join_room', params.roomId);

    socketRef.current.on('receive_message', (msg) => {
      setMessages((prev) => [...prev, msg]);
    });

    socketRef.current.on('user_typing', (username) => {
      setTypingUser(username);
      setTimeout(() => setTypingUser(null), 3000);
    });

    return () => socketRef.current.disconnect();
  }, [params.roomId]);

  const filteredMessages = messages.filter((msg) =>
    msg.text.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="container-fluid h-100">
      <div className="row h-100">
        {/* Sidebar: Participants */}
        <div className="col-12 col-md-3 bg-dark-custom text-white border-end border-crimson p-3">
          <ParticipantsList roomId={params.roomId} />
        </div>

        {/* Main Chat Area */}
        <div className="col-12 col-md-9 d-flex flex-column p-0">
          {/* Room Info */}
          <div className="bg-dark-custom p-3 border-bottom border-crimson">
            <h5 className="text-crimson mb-1">{roomInfo.name}</h5>
            <small className="text-white-50">{roomInfo.description || 'Chat and connect in real time!'}</small>
          </div>

          {/* Search bar */}
          <div className="p-3 bg-black border-bottom border-crimson">
            <input
              type="text"
              className="form-control bg-black text-white border-crimson"
              placeholder="Search messages..."
              value={search}
              onChange={(e) => setSearch(e.target.value)}
            />
          </div>

          {/* Messages */}
          <div className="flex-grow-1 overflow-auto p-3 bg-black text-white" style={{ maxHeight: '65vh' }}>
            {filteredMessages.map((msg, i) => (
              <div key={i} className="mb-3">
                <strong className="text-crimson">{msg.username}:</strong> <span>{msg.text}</span>
                <div className="text-muted small">{new Date(msg.timestamp).toLocaleTimeString()}</div>
              </div>
            ))}
            {typingUser && (
              <div className="text-muted fst-italic">
                {typingUser} is typing...
              </div>
            )}
          </div>

          {/* Message input */}
          <div className="border-top border-crimson p-3 bg-dark-custom">
            <MessageInput socket={socketRef.current} roomId={params.roomId} />
          </div>
        </div>
      </div>
    </div>
  );
}

'use client';

import { useState, useEffect } from 'react';

export default function MessageInput({ socket, roomId }) {
  const [message, setMessage] = useState('');
  const [typingTimeout, setTypingTimeout] = useState(null);

  const sendMessage = () => {
    if (!message.trim()) return;

    socket.emit('send_message', {
      roomId,
      text: message,
      timestamp: new Date().toISOString(),
    });

    setMessage('');
  };

  // Notify server user is typing
  const handleTyping = () => {
    socket.emit('typing', { roomId });

    if (typingTimeout) clearTimeout(typingTimeout);

    setTypingTimeout(
      setTimeout(() => {
        // Optionally, send a 'stop_typing' event here if you want
      }, 2000)
    );
  };

  // Send on Enter key
  const handleKeyDown = (e) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      sendMessage();
    }
  };

  return (
    <div className="d-flex gap-2">
      <textarea
        className="form-control bg-black text-white border-crimson"
        rows="1"
        placeholder="Type a message..."
        value={message}
        onChange={(e) => {
          setMessage(e.target.value);
          handleTyping();
        }}
        onKeyDown={handleKeyDown}
        style={{ resize: 'none' }}
      />
      <button
        className="btn btn-primary"
        onClick={sendMessage}
        disabled={!message.trim()}
        aria-label="Send message"
      >
        Send
      </button>
    </div>
  );
}

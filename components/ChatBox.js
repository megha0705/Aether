"use client";

import { useEffect, useRef, useState } from "react";
import { Stomp } from "@stomp/stompjs";
import SockJS from "sockjs-client";
// import ParticipantsList from './ParticipantsList'; // Make sure this component exists

export default function ChatBox({ roomId, token }) {
  const [messages, setMessages] = useState([]);
  const stompClientRef = useRef(null);
  const [newMsg, setNewMsg] = useState("");

  useEffect(() => {
    const socket = new SockJS("http://localhost:8080/ws");
    const client = Stomp.over(socket);

    client.debug = () => {}; // Disable verbose logs

    client.connect(
      { Authorization: `Bearer ${token}` },
      () => {
        client.subscribe(`/topic/room/${roomId}`, (msg) => {
          if (msg.body) {
            const body = JSON.parse(msg.body);
            setMessages((prev) => [...prev, body]);
          }
        });
      },
      (error) => {
        console.error("STOMP connection error:", error);
      }
    );

    stompClientRef.current = client;

    return () => {
      if (stompClientRef.current) {
        stompClientRef.current.disconnect();
        stompClientRef.current = null;
      }
    };
  }, [roomId, token]);

  const sendMessage = () => {
    const client = stompClientRef.current;
    if (
      client &&
      client.connected && // ✅ ensure connection is established
      newMsg.trim() !== ""
    ) {
      const messagePayload = {
        roomId,
        content: newMsg.trim(),
        timestamp: new Date().toISOString(),
      };

      client.send(
        "/app/message",
        { Authorization: `Bearer ${token}` },
        JSON.stringify(messagePayload)
      );
      setNewMsg("");
    }
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter" && !e.shiftKey) {
      e.preventDefault();
      sendMessage();
    }
  };

  return (
    <div className="d-flex flex-grow-1">
      {/* Chat area */}
      <div className="flex-grow-1 d-flex flex-column bg-dark-custom p-3 text-white">
        <div
          className="flex-grow-1 overflow-auto mb-3"
          style={{ maxHeight: "calc(100vh - 200px)" }}
        >
          {messages.map((msg, idx) => (
            <div key={idx} className="mb-2">
              <strong>{msg.sender || "Anon"}:</strong> {msg.content}
              <br />
              <small className="text-muted">
                {new Date(msg.timestamp).toLocaleTimeString()}
              </small>
            </div>
          ))}
        </div>
        <div className="d-flex">
          <textarea
            className="form-control me-2"
            value={newMsg}
            onChange={(e) => setNewMsg(e.target.value)}
            onKeyDown={handleKeyDown}
            placeholder="Type a message..."
            rows={2}
            style={{ resize: "none" }}
          />
          <button
            className="btn btn-crimson"
            onClick={sendMessage}
            disabled={!newMsg.trim()}
          >
            Send
          </button>
        </div>
      </div>
    </div>
  );
}

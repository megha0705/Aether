'use client';

import { useContext, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { AuthContext } from '../context/AuthContext';
import Image from 'next/image';

export default function HomePage() {
  const { user } = useContext(AuthContext);
  const router = useRouter();

  useEffect(() => {
    if (user) router.push('/dashboard');
  }, [user]);

  return (
    <div className="container text-center py-5">
      <h1 className="display-4 text-crimson fw-bold mb-3">Welcome to 
        <Image
          src="/aether1.png"         // From public folder
          alt="Aether Chat Logo"
          width={500}               // Adjust width as needed
          height={125}               // Adjust height as needed
          priority                  // Loads image faster
        /></h1>
      <p className="lead text-light">Your space to connect and chat with others in real time</p>
      <div className="mt-4 d-flex justify-content-center gap-3">
        <button onClick={() => router.push('/login')} className="btn btn-primary btn-lg">
          Login
        </button>
        <button onClick={() => router.push('/register')} className="btn btn-outline-light btn-lg">
          Register
        </button>
      </div>
    </div>
  );
}

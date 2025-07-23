'use client';

import { useState, useContext } from 'react';
import axios from 'axios';
import { useRouter } from 'next/navigation';
import { AuthContext } from '../../../context/AuthContext';

export default function RegisterPage() {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
  });

  const [error, setError] = useState('');
  const router = useRouter();
  const { login } = useContext(AuthContext);

  const handleChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    if (formData.password !== formData.confirmPassword) {
      setError('Passwords do not match.');
      return;
    }

    const body = new URLSearchParams();
    body.append('userName', formData.username);
    body.append('email', formData.email);
    body.append('password', formData.password);

    try {
      await axios.post('http://localhost:8080/register', body, {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      });

      router.push('/login');
    } catch (err) {
      setError(err?.response?.data?.message || 'Registration failed.');
    }
  };

  return (
    <div className="container d-flex align-items-center justify-content-center min-vh-100">
      <div className="w-100" style={{ maxWidth: '500px' }}>
        <div className="card bg-dark-custom text-white border border-crimson p-4">
          <h2 className="mb-4 text-center text-crimson">Create Account</h2>

          {error && (
            <div className="alert alert-danger text-center" role="alert">
              {error}
            </div>
          )}

          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="username" className="form-label text-white">
                Username
              </label>
              <input
                type="text"
                className="form-control bg-black text-white border-crimson"
                id="username"
                name="username"
                value={formData.username}
                onChange={handleChange}
                required
              />
            </div>

            <div className="mb-3">
              <label htmlFor="email" className="form-label text-white">
                Email
              </label>
              <input
                type="email"
                className="form-control bg-black text-white border-crimson"
                id="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                required
              />
            </div>

            <div className="mb-3">
              <label htmlFor="password" className="form-label text-white">
                Password
              </label>
              <input
                type="password"
                className="form-control bg-black text-white border-crimson"
                id="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
                required
              />
            </div>

            <div className="mb-4">
              <label htmlFor="confirmPassword" className="form-label text-white">
                Confirm Password
              </label>
              <input
                type="password"
                className="form-control bg-black text-white border-crimson"
                id="confirmPassword"
                name="confirmPassword"
                value={formData.confirmPassword}
                onChange={handleChange}
                required
              />
            </div>

            <button type="submit" className="btn btn-primary w-100">
              Register
            </button>
          </form>

          <div className="mt-3 text-center">
            <p className="text-white">
              Already have an account?{' '}
              <a href="/login" className="text-crimson fw-bold" style={{ textDecoration: 'underline' }}>
                Login here
              </a>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}

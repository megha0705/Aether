export function setToken(token) {
  if (typeof window !== "undefined") {
    localStorage.setItem('token', token);
  }
}

export function getToken() {
  if (typeof window !== "undefined") {
    return localStorage.getItem('token');
  }
  return null;
}

export function clearToken() {
  if (typeof window !== "undefined") {
    localStorage.removeItem('token');
  }
}

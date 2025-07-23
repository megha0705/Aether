import "./globals.css";
import AuthProvider from "../context/AuthContext";
import Script from 'next/script';

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <head>
        {/* Other head tags */}
        <Script
          src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
          strategy="beforeInteractive"
        />
      </head>
      <body className="d-flex flex-column min-vh-100 bg-black text-white">
        <AuthProvider>{children}</AuthProvider>
      </body>
    </html>
  );
}

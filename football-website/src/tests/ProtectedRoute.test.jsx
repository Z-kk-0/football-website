const { JSDOM } = require("jsdom");
import { cleanup, render, screen } from '@testing-library/react';
import { MemoryRouter, Routes, Route } from 'react-router-dom';
import ProtectedRoute from '../modules/ProtectedRoute';
import { useAuth } from '../functions/AuthContext';

jest.mock('../functions/AuthContext', () => ({
  useAuth: jest.fn(),
}));

afterEach(cleanup);

it('redirects unauthenticated users to /nologin', () => {
  useAuth.mockReturnValue({ user: null });

  render(
    <MemoryRouter initialEntries={['/secret']}>
      <Routes>
        <Route
          path="/secret"
          element={
            <ProtectedRoute>
              <div>secret page</div>
            </ProtectedRoute>
          }
        />
        <Route path="/nologin" element={<div>nologin page</div>} />
      </Routes>
    </MemoryRouter>
  );

  expect(screen.getByText(/nologin page/i)).toBeTruthy();
});

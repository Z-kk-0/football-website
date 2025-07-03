import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import userEvent from "@testing-library/user-event";
import { useAuth } from "../functions/AuthContext";
import Home from "../pages/Home";

jest.mock("../functions/AuthContext", () => ({ useAuth: jest.fn() }));

const mockNavigate = jest.fn();

jest.mock("react-router-dom", () => {
  const original = jest.requireActual("react-router-dom");
  return { ...original, useNavigate: () => mockNavigate };
});

describe("Home component", () => {
  let isAdmin = false;

  beforeEach(() => {
    mockNavigate.mockReset();
    isAdmin = false;
    useAuth.mockImplementation(() => ({
      hasAnyRole: (roles) => isAdmin && roles.includes("ROLE_ADMIN"),
    }));
  });

  it("renders buttons correctly and triggers navigation to the expected routes", async () => {
    const { rerender } = render(<Home />);

    expect(
      screen.getByRole("button", { name: /playbook/i })
    ).toBeInTheDocument();
    expect(
      screen.queryByRole("button", { name: /members/i })
    ).toBeNull();

    isAdmin = true;
    rerender(<Home />);

    const membersBtn = screen.getByRole("button", { name: /Mitglieder/i });
    expect(membersBtn).toBeInTheDocument();

    await userEvent.click(screen.getByRole("button", { name: /Playbook/i }));
    expect(mockNavigate).toHaveBeenCalledWith("/plays");

    await userEvent.click(membersBtn);
    expect(mockNavigate).toHaveBeenCalledWith("/members");
  });
});

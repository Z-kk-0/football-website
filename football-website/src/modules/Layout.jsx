import { Outlet } from "react-router-dom"
import Navigation from "./Navigation"

/**
 * Layout component that wraps the app's navigation and main content.
 *
 * - Renders the Navigation bar and the current route's content via <Outlet>.
 *
 * @component
 * @returns {JSX.Element} The layout with navigation and content.
 */

export default function Layout(){
  return(
    <div>
      <Navigation />
      <Outlet />
    </div>
  )
}
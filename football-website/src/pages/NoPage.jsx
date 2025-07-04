import deploy from '../assets/deploy_what_could_go_wrong.jpg'

/**
 * NoPage (404) page component.
 *
 * - Displays a 404 error message and an image.
 *
 * @component
 * @returns {JSX.Element} The rendered 404 page.
 */
export default function NoPage() {
  return (
    <div>
      <h1>404 - Page not found</h1>
      <img src={deploy} />
    </div>
  )
}
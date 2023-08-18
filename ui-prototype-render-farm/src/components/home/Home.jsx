import React, { useEffect, useState } from 'react'
import { Container } from 'semantic-ui-react'
import TaskList from '../tasks/TaskList'
import { tasksApi } from '../misc/TaskApi'
import keycloak from '../security/keycloak'

function Home() {
  const [tasks, setTasks] = useState([])
  const [error, setError] = useState('')
  const [isLoading, setLoading] = useState(true)

  useEffect(() => {
    ;(async () => {
      try {
        const response = await tasksApi.getTasks(keycloak.token)
        const tasks = await response.data
        setTasks(tasks)
      } catch {
        setError(error.message)
      }
      setLoading(false)
    })()
  }, [])

  if (error) {
    return <Container>Error: {error}</Container>
  }

  return isLoading ? (
    <></>
  ) : (
    <Container>
      <TaskList tasks={tasks} />
    </Container>
  )
}

export default Home

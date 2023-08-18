import React, { useEffect, useState } from 'react'
import { Container, Grid } from 'semantic-ui-react'
import { handleLogError } from '../misc/Helpers'
import { useParams } from 'react-router-dom'
import { useKeycloak } from '@react-keycloak/web'
import TaskCard from './TaskCard'
import { tasksApi } from '../misc/TaskApi'

function TaskDetail() {
  const [authenticated, setAuthenticated] = useState(false)
  const [task, setTask] = useState(null)

  const { keycloak } = useKeycloak()
  const { id } = useParams()

  useEffect(() => {
    const taskId = id
    setAuthenticated(keycloak.authenticated)
    const fetchTask = async () => {
      try {
        const response = await tasksApi.getTask(taskId, keycloak.token)
        const task = response.data
        setTask(task)
      } catch (error) {
        handleLogError(error)
      }
    }
    fetchTask()
  }, [id, keycloak.authenticated])

  return !task ? (
    <></>
  ) : (
    <Container>
      <Grid columns={2} stackable>
        <Grid.Row>
          <Grid.Column width={5}>
            <TaskCard task={task} link={false} />
          </Grid.Column>
        </Grid.Row>
      </Grid>
    </Container>
  )
}

export default TaskDetail

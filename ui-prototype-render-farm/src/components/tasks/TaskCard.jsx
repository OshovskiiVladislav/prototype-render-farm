import React from 'react'
import { Card, Image } from 'semantic-ui-react'
import { Link } from 'react-router-dom'

function TaskCard({ task, link }) {
  const content = (
    <>
      <Image
        src={task.poster ? task.poster : '/images/task-title.png'}
        wrapped
        ui={false}
      />
      <Card.Content textAlign="center">
        <Card.Header>{task.title}</Card.Header>
      </Card.Content>
      <Card.Content>
        <Card.Description>
          ID: <strong>{task.id}</strong>
        </Card.Description>
        <Card.Description>
          Type: <strong>{task.type}</strong>
        </Card.Description>
      </Card.Content>
    </>
  )

  return !link ? (
    <Card>{content}</Card>
  ) : (
    <Card as={Link} to={`/tasks/${task.id}`}>
      {content}
    </Card>
  )
}

export default TaskCard

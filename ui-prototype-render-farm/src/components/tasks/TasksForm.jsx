import React from 'react'
import { Button, Form } from 'semantic-ui-react'

function TasksForm({ form, handleChange, handleSaveTask, clearForm }) {
  return (
    <Form>
      <Form.Input
        fluid
        label="Title *"
        id="title"
        onChange={handleChange}
        value={form.title}
        error={form.titleError}
      />
      <Form.Input
        fluid
        label="Type *"
        id="type"
        onChange={handleChange}
        value={form.type}
        error={form.typeError}
      />
      <Button.Group fluid>
        <Button onClick={clearForm}>Cancel</Button>
        <Button.Or />
        <Button positive onClick={handleSaveTask}>
          Save
        </Button>
      </Button.Group>
    </Form>
  )
}

export default TasksForm

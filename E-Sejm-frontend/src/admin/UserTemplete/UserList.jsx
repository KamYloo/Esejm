import React from 'react';
import { List, Datagrid, TextField, EmailField, EditButton, DeleteButton} from 'react-admin';

export const UserList = () => (
    <List>
        <Datagrid rowClick="edit">
            <TextField source="id" label="ID" />
            <TextField source="firstName" label="First Name" />
            <TextField source="lastName" label="Last Name" />
            <TextField source="nickName" label="Nickname" />
            <EmailField source="email" label="Email" />
            <EditButton />
            <DeleteButton />
        </Datagrid>
    </List>
);


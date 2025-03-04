import React from 'react';
import { SimpleForm, TextInput, Edit, SelectArrayInput } from 'react-admin';
import {Card, CardContent, CardHeader, Grid} from '@mui/material';

export const UserEdit = () => (
    <Edit
        transform={(data) => {
            const selectedRoles = Array.isArray(data.roles) ? data.roles : [];
            const cleanedRoles = selectedRoles.filter(role => typeof role === 'string');

            return {
                ...data,
                roles: cleanedRoles,
            };
        }}
    >
        <Card>
            <CardHeader title="Edit User Details" />
            <CardContent>
                <SimpleForm>
                    <Grid container spacing={2}>
                        <Grid item xs={12} md={6}>
                            <TextInput
                                fullWidth
                                source="firstName"
                                label="First Name"
                            />
                        </Grid>
                        <Grid item xs={12} md={6}>
                            <TextInput
                                fullWidth
                                source="lastName"
                                label="Last Name"
                            />
                        </Grid>
                        <Grid item xs={12} md={6}>
                            <TextInput
                                fullWidth
                                source="nickName"
                                label="Nickname"
                            />
                        </Grid>
                        <Grid item xs={12} md={6}>
                            <TextInput
                                fullWidth
                                source="email"
                                label="E-mail"
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <SelectArrayInput
                                fullWidth
                                source="roles"
                                label="Roles"
                                choices={[
                                    { id: 'ADMIN', name: 'Admin' },
                                    { id: 'USER', name: 'User' },
                                ]}
                            />
                        </Grid>
                    </Grid>
                </SimpleForm>
            </CardContent>
        </Card>
    </Edit>
);

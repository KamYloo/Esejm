import React from 'react';
import { Card, CardContent, Typography, Box } from '@mui/material';

const Dashboard = () => {
    return (
        <Box sx={{ p: 3 }}>
            <Card sx={{ mb: 3, boxShadow: 3 }}>
                <CardContent>
                    <Typography variant="h5" color="primary">
                        Witaj w panelu administracyjnym!
                    </Typography>
                    <Typography variant="body1">
                        Tutaj możesz zarządzać użytkownikami, wiadomościami i innymi zasobami.
                    </Typography>
                </CardContent>
            </Card>
        </Box>
    );
};

export {Dashboard}
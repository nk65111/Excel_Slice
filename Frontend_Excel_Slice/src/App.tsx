// src/App.tsx
import React from 'react';
import FileUpload from './FileUpload';
import { CssBaseline, ThemeProvider, createTheme, Box } from '@mui/material';
import Img from '../public/undraw_engineering_team_a7n2.svg';

const theme = createTheme({
  palette: {
    primary: {
      main: '#52595D', // Custom primary color
    },
    background: {
      default: '#ADD8E6', // Light background color
    },
  },
  typography: {
    h4: {
      fontWeight: 600,
    },
  },
});

const App: React.FC = () => {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Box
        sx={{
          height: '100vh',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          position: 'relative',
          overflow: 'hidden',
          padding: 2,
          backgroundColor: theme.palette.background.default,
          marginLeft: '4rem',
        }}
      >
        
        <FileUpload />

        <div style={{
          marginLeft: '2rem',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
        }}>
        <img src={Img} alt="" />

        </div>
      </Box>
    </ThemeProvider>
  );
};

export default App;

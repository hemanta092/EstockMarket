import React, { useState } from "react";
import { useDispatch } from "react-redux";
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Alert from '@mui/material/Alert';
import Box from '@mui/material/Box';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { authenticateUser } from '../services/Actions';
import stock from '../img/stock.jpg';
import stockLogo from '../img/stockLogo.jpg'
import { Route } from "react-router";
import { useNavigate } from 'react-router-dom';

const theme = createTheme();
const styles = {
  paperContainer: {
    backgroundImage: `url(${stock})`,
    backgroundSize: "auto",
    height: "100vh",
    color: "#f5f5f5"
  }
};

export default function SignIn() {
  const [alert, setAlert] = useState(false);
  const [alertContent, setAlertContent] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const user = { email, password };
  const handleSubmit = (event) => {
    event.preventDefault();
    console.log({
      email: user.email,
      password: user.password,
    });

  };
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const userSignIn = () => {
    const re = authenticateUser(user);
    dispatch(re)
      .then((response) => {
        const roles = JSON.parse(sessionStorage.getItem('token'))?.roles.split(',');
        if (response.status === 200 && (roles.includes("USER") || roles.includes("ADMIN"))) {
          <Route render={(navigate('/stockView', { replace: true },{state: { fromApp: true } }))} />
        }
        setAlert(false);
      }
      )
      .catch((error) => {
        console.log(error);
        setAlertContent("User Not Found Or Incorrect Password");
        setAlert(true);
      });
  };


  return (
    <div style={styles.paperContainer}>
      <ThemeProvider theme={theme} >
        <Box sx={{ flexGrow: 1 }}>
          <AppBar position="static">
            <Toolbar>
            <img style={{ maxWidth: 80,  marginRight: '20px'}}  src={stockLogo} alt="EStock Market Application" />
              <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                EStock Market Application
              </Typography>
            </Toolbar>
          </AppBar>
        </Box>

        <Container component="main" maxWidth="sm"
        >
          <CssBaseline />
          <Box
            sx={{
              marginTop: 8,
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
            }}

          >
            <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
              <LockOutlinedIcon />
            </Avatar>
            <Typography component="h1" variant="h5">
              Sign in
            </Typography>
            <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
              {alert ? <Alert severity='error'>{alertContent}</Alert> : <></>}
              <TextField
                margin="normal"
                required
                fullWidth
                id="email"
                label="Email Id"
                name="email"
                autoComplete="email"
                onChange={(e) => setEmail(e.target.value)}
                autoFocus
              />
              <TextField
                margin="normal"
                required
                fullWidth
                name="password"
                label="Password"
                type="password"
                id="password"
                autoComplete="current-password"
                onChange={(e) => setPassword(e.target.value)}
              />
              <Button
                type="submit"
                fullWidth
                variant="contained"
                sx={{ mt: 3, mb: 2 }}
                onClick={userSignIn}
              >
                Sign In
              </Button>
              <Grid container>
                <Grid item>
                  <Link href="/SignUp" variant="body2">
                    {"Don't have an account? Sign Up"}
                  </Link>
                </Grid>
              </Grid>
            </Box>
          </Box>
        </Container>

      </ThemeProvider>
    </div>
  );
}
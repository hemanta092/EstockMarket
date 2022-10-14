import React, { useState } from "react";
import { useDispatch } from "react-redux";
import Avatar from '@mui/material/Avatar';
import Alert from '@mui/material/Alert';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { registerUser } from '../services/Actions';
import OutlinedInput from '@mui/material/OutlinedInput';
import FormControl from '@mui/material/FormControl';
import Chip from '@mui/material/Chip';
import { Route } from "react-router";
import { useNavigate } from 'react-router-dom';
import stock from '../img/stock.jpg';
import stockLogo from '../img/stockLogo.jpg'

const theme = createTheme();
const styles = {
    paperContainer: {
        backgroundImage: `url(${stock})`,
        backgroundSize: "auto",
        height: "100vh",
        color: "#f5f5f5"
    }
};
export default function SignUp() {
    const [alert, setAlert] = useState(false);
    const [alertContent, setAlertContent] = useState('');
    const userRoles = ['ADMIN', 'USER'];
    const [roles, setRoles] = React.useState([]);
    const initialState = {
        userName: "",
        email: "",
        password: "",
        roles: "",
    };

    const [user, setUser] = useState(initialState);

    const userChange = (event) => {
        const { name, value } = event.target;
        setUser({ ...user, [name]: value });
    };

    const handleChange = (event) => {
        const {
            target: { value },
        } = event;
        setRoles(
            // On autofill we get a stringified value.
            typeof value === 'string' ? value.split(',') : value,
        );
        console.log(value);
    };

    const dispatch = useDispatch();
    const navigate = useNavigate();

    const saveUser = (event) => {
        event.preventDefault();
        user.roles = roles.toString();
        dispatch(registerUser(user))
            .then((response) => {
                if (response.status === 200) {
                    <Route render={response.status === 200 ? (navigate('/signIn', { replace: true })) : (navigate('/signUp', { replace: true }))} />
                }
                else {
                    setAlertContent("Unable To Save");
                    setAlert(true);
                }
            })
            .catch((error) => {
                console.log(error);
                setAlertContent("Unable To Save");
                setAlert(true);
            });
    };

    return (
        <div style={styles.paperContainer}>
            <ThemeProvider theme={theme}>
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

                <Container component="main" maxWidth="sm">
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
                            Sign up
                        </Typography>
                        <Box component="form" noValidate onSubmit={saveUser} sx={{ mt: 3 }}>
                            {alert ? <Alert severity='error'>{alertContent}</Alert> : <></>}
                            <Grid container spacing={2}>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        autoComplete="given-name"
                                        name="userName"
                                        required
                                        fullWidth
                                        id="userName"
                                        label="User Name"
                                        onChange={userChange}
                                        autoFocus
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <TextField
                                        required
                                        fullWidth
                                        id="email"
                                        label="Email Address"
                                        name="email"
                                        onChange={userChange}
                                        autoComplete="email"
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <TextField
                                        required
                                        fullWidth
                                        id="phoneNumber"
                                        label="Phone Number"
                                        name="phoneNumber"
                                        onChange={userChange}
                                        autoComplete="phoneNumber"
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <div>
                                        <FormControl sx={{ m: 1, width: 300 }}>
                                            <InputLabel id="multiple-label">Roles</InputLabel>
                                            <Select
                                                labelId="multiple-label"
                                                id="multiple-label"
                                                multiple
                                                value={roles}
                                                onChange={handleChange}
                                                input={<OutlinedInput id="multiple-label" label="Roles" />}
                                                renderValue={(selected) => (
                                                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5 }}>
                                                        {selected.map((value) => (
                                                            <Chip key={value} label={value} />
                                                        ))}
                                                    </Box>
                                                )}
                                            >
                                                {userRoles.map((role) => (
                                                    <MenuItem
                                                        key={role}
                                                        value={role}
                                                    >
                                                        {role}
                                                    </MenuItem>
                                                ))}
                                            </Select>
                                        </FormControl>
                                    </div>
                                </Grid>
                                <Grid item xs={12}>
                                    <TextField
                                        required
                                        fullWidth
                                        name="password"
                                        label="Password"
                                        type="password"
                                        id="password"
                                        onChange={userChange}
                                        autoComplete="new-password"
                                    />
                                </Grid>
                            </Grid>
                            <Button
                                type="submit"
                                fullWidth
                                variant="contained"
                                sx={{ mt: 3, mb: 2 }}
                            >
                                Register
                            </Button>
                            <Grid container justifyContent="flex-end">
                                <Grid item>
                                    <Link href="/SignIn" variant="body2">
                                        Already have an account?Sign in
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

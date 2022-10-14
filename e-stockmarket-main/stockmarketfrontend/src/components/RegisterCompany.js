import React, { useState } from "react";
import { useDispatch } from "react-redux";
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import Alert from '@mui/material/Alert';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import { Link, Navigate, useLocation } from "react-router-dom";
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { registerCompany } from '../services/Actions';
import AppRegistrationOutlined from "@mui/icons-material/AppRegistrationOutlined";
import stock from '../img/stock.jpg';
import stockLogo from '../img/stockLogo.jpg';
import IconButton from '@mui/material/IconButton';
import LogoutIcon from '@mui/icons-material/Logout';


const theme = createTheme();
const styles = {
    paperContainer: {
      backgroundImage: `url(${stock})`,
      backgroundSize: "auto",
      height: "100vh",
      color: "#f5f5f5"
    }
  };
export default function RegisterCompany() {
    const [alert, setAlert] = useState(false);
    const [alertContent, setAlertContent] = useState('');
    const initialState = {
        companyName: "",
        companyCode: "",
        companyCEO: "",
        companyTurnOver: "",
        companyWebsite: "",
        stockExchange: "",
    };

    const [company, setCompany] = useState(initialState);

    const companyChange = (event) => {
        const { name, value } = event.target;
        setCompany({ ...company, [name]: value });
    };

    const dispatch = useDispatch();
  
    const saveCompany = () => {
        const re = registerCompany(company);
        console.log(re);
        dispatch(re)
            .then((response) => {
                setAlert(true);
                setAlertContent("Company Saved Successfully");
            })
            .catch((error) => {
                console.log(error);
            });
    };
    function clear()
    {
        sessionStorage.clear();
    }

    
    const  state = useLocation();
    
  if (state?.key === 'default' && sessionStorage.getItem('token') === null) {
    return <Navigate to="/" />;
  }

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
                        <Box
                                display="flex"
                                justifyContent="center"
                                alignItems="center"
                                minHeight="10vh">
                                <Link style={{ textDecoration: 'none' }} to='/stockView'>
                                    <Button variant="contained" size="medium">
                                        Stock View
                                    </Button>
                                </Link>
                            </Box>
                            <IconButton component = {Link} to='/SignIn' onClick = {clear} >
                            <LogoutIcon/>
                            </IconButton>
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
                        <AppRegistrationOutlined />
                    </Avatar>
                    <Typography component="h1" variant="h5">
                        Register Company
                    </Typography>
                    <Box component="form" noValidate onSubmit={saveCompany} sx={{ mt: 3 }}>
                    {alert ? <Alert severity='success'>{alertContent}</Alert>:<></> }
                        <Grid container spacing={2}>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    autoComplete="given-name"
                                    name="companyName"
                                    required
                                    fullWidth
                                    id="companyName"
                                    label="Company Name"
                                    onChange={companyChange}
                                    autoFocus
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    required
                                    fullWidth
                                    id="companyCode"
                                    label="Company Code"
                                    name="companyCode"
                                    onChange={companyChange}
                                    autoComplete="companyCode"
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    required
                                    fullWidth
                                    name="companyCEO"
                                    label="Company CEO"
                                    type="companyCEO"
                                    id="companyCEO"
                                    onChange={companyChange}
                                    autoComplete="companyCEO"
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    required
                                    fullWidth
                                    name="companyWebsite"
                                    label="Company Website"
                                    type="companyWebsite"
                                    id="companyWebsite"
                                    onChange={companyChange}
                                    autoComplete="companyWebsite"
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    required
                                    fullWidth
                                    name="companyTurnOver"
                                    label="Company TurnOver"
                                    type="companyTurnOver"
                                    id="companyTurnOver"
                                    onChange={companyChange}
                                    autoComplete="companyTurnOver"
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    required
                                    fullWidth
                                    name="stockExchange"
                                    label="Stock Exchange"
                                    type="stockExchange"
                                    id="stockExchange"
                                    onChange={companyChange}
                                    autoComplete="stockExchange"
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
                    </Box>
                </Box>
            </Container>
        </ThemeProvider>
        </div>
    );
}

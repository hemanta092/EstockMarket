import React, { useState } from "react";
import { Link, Navigate, useLocation } from "react-router-dom";
import { useDispatch } from "react-redux";
import AppBar from '@mui/material/AppBar';
import Alert from '@mui/material/Alert';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Stack from '@mui/material/Stack';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import TextField from '@mui/material/TextField';
import LogoutIcon from '@mui/icons-material/Logout';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DesktopDatePicker } from '@mui/x-date-pickers/DesktopDatePicker';
import Button from '@mui/material/Button';
import { styled } from '@mui/material/styles';
import Container from '@mui/material/Container';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import IconButton from '@mui/material/IconButton';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import Box from '@mui/material/Box';
import stock from '../img/stock.jpg';
import stockLogo from '../img/stockLogo.jpg'
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { searchStocks, getCompanyNames } from "../services/Actions";
import { useEffect } from "react";

export default function StockView() {
    const [companyName, setCompanyName] = React.useState('');
    const [startDate, setStartDate] = React.useState(null);
    const [endDate, setEndDate] = React.useState(null);
    const styles = {
        paperContainer: {
            backgroundImage: `url(${stock})`,
            backgroundSize: "auto",
            height: "100vh",
            color: "#f5f5f5"
        }
    };
    const handleChange = (event) => {
        setCompanyName(event.target.value);
        console.log("company Name", companyName);
    };
    const theme = createTheme({
        palette: {
            action: {
                disabledBackground: '#efebe9',
                disabled: '#424242'
            },
          },}
    );

    const StyledTableCell = styled(TableCell)(({ theme }) => ({
        [`&.${tableCellClasses.head}`]: {
            backgroundColor: theme.palette.primary.light,
            color: theme.palette.common.white,
        },
        [`&.${tableCellClasses.body}`]: {
            fontSize: 14,
        },
    }));

    const StyledTableRow = styled(TableRow)(({ theme }) => ({
        '&:nth-of-type(odd)': {
            backgroundColor: theme.palette.action.hover,
        },
        // hide last border
        '&:last-child td, &:last-child th': {
            border: 0,
        },
    }));

    const [data, setData] = useState([]);
    const [stocks, setStocks] = useState([]);
    const [companyNames, setCompanyNames] = useState([]);
    const dispatch = useDispatch();
    const [alert, setAlert] = useState(false);
    const [alertContent, setAlertContent] = useState('');

    useEffect(() => {
        dispatch(getCompanyNames())
            .then(response => {
                setCompanyNames(Object.values(response));
            })
            .catch(error => {
                console.log('Something went wrong during company Names', error);
            })
    }, [dispatch]);


    const handleSubmit = () => {
        const search = { companyName, startDate, endDate };
        console.log(search);
        const re = searchStocks(search);

        dispatch(re).then(response => {
            console.log(response);
            if (response.isSuccessful) {
                console.log('Printing Stock data', response);
                setData(Object.values(response));
                setStocks((JSON.stringify(Object.values(response))[3]) === '{}' ? [] : Object.values(response)[3]);
                setAlert(false);
            }
            else {
                setStocks([]);
                setData([]);
                setAlertContent("No stocks found for company");
                setAlert(true);
            }
        })
            .catch((error) => {
                console.log('Something went wrong', error.message);
                setStocks([]);
                setData([]);
                setAlertContent("No stocks found for company");
                setAlert(true);
            })
    }
    function convert(str) {
        var date = new Date(str);
        return date.getTime();
    }

    function clear()
    {
        sessionStorage.clear();
    }

    const isDisabled = !(JSON.parse(sessionStorage.getItem('token'))?.roles.split(',').includes("ADMIN"));

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
                                    <Button variant="contained" size="medium" disabled= {isDisabled} style={{ textDecoration: 'none', margin: 35 }} 
                                    component = {Link} to='/registerCompany'>
                                        Register Company
                                    </Button>
                                    <Button variant="contained" size="medium" disabled= {isDisabled} style={{ textDecoration: 'none', margin: 35 }} 
                                    component = {Link} to='/registerStock'>
                                        Register Stock
                                    </Button>
                            </Box>
                            <IconButton component = {Link} to='/SignIn' onClick = {clear} >
                            <LogoutIcon/>
                            </IconButton>
                        </Toolbar>

                    </AppBar>
                </Box>
                <div style={{ marginTop: 80 }}>
                    <Container component="main" maxWidth="md">
                        <Stack spacing={3}>
                            {alert ? <Alert severity='error'>{alertContent}</Alert> : <></>}
                            <FormControl>
                                <InputLabel id="label">CompanyName</InputLabel>
                                <Select
                                    labelId="label"
                                    id="select"
                                    value={companyName}
                                    label="CompanyName"
                                    onChange={handleChange}
                                >
                                    {companyNames?.map((name, idx) => (
                                        <MenuItem
                                            key={idx} value={name}>
                                            {name}
                                        </MenuItem>
                                    ))}


                                </Select>
                            </FormControl>
                        </Stack>
                        <LocalizationProvider dateAdapter={AdapterDateFns}>
                            <Box
                                display="flex"
                                justifyContent="center"
                                alignItems="center"
                                minHeight="10vh"
                            >
                                <DesktopDatePicker
                                    label="Start Date"
                                    value={startDate}
                                    onChange={(newValue) => {
                                        setStartDate(convert(newValue));
                                    }}
                                    renderInput={(params) => <TextField {...params} />}
                                />
                                <DesktopDatePicker
                                    label="End Date"
                                    value={endDate}
                                    onChange={(newValue) => {
                                        setEndDate(convert(newValue));
                                    }}
                                    renderInput={(params) => <TextField {...params} />}
                                />
                            </Box>
                        </LocalizationProvider>
                        <Box
                            display="flex"
                            justifyContent="center"
                            alignItems="center"
                            minHeight="10vh"
                        >
                            <Button variant="contained" size="medium" disabled = { startDate>endDate } onClick={handleSubmit}> 
                                Search
                            </Button>
                        </Box>
                        {stocks.length !== 0 && (<TableContainer component={Paper}>
                            <Table sx={{ minWidth: 150 }} aria-label="simple table">
                                <TableHead>
                                    <StyledTableRow>
                                        <StyledTableCell align="left">Stock Price</StyledTableCell>
                                        <StyledTableCell align="left">Date</StyledTableCell>
                                        <StyledTableCell align="left">Time</StyledTableCell>
                                    </StyledTableRow>
                                </TableHead>
                                <TableBody>
                                    {stocks?.map((row, index) => (
                                        <StyledTableRow
                                            key={index}
                                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                        >
                                            <StyledTableCell component="th" scope="row">
                                                {row.stockPrice}
                                            </StyledTableCell>
                                            <StyledTableCell align="left">{new Date(row.createdOn).toLocaleDateString()}</StyledTableCell>
                                            <StyledTableCell align="left">{new Date(row.createdOn).toLocaleTimeString()}</StyledTableCell>
                                        </StyledTableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>)}
                        {stocks.length !== 0 && (<Box
                            component="form"
                            sx={{
                                '& > :not(style)': { m: 1, width: '25ch' },
                            }}
                            noValidate
                            autoComplete="off"
                        >
                            <TextField label="Max Stock" color="secondary" value={data[4]?.maxStockPrice} readOnly focused />
                            <TextField label="Min Stock" color="secondary" value={data[4]?.minStockPrice} readOnly focused />
                            <TextField label="Avg Stock" color="secondary" value={data[4]?.avgStockPrice} readOnly focused />
                        </Box>)}
                    </Container>
                </div>
            </ThemeProvider>
        </div>
    );
}
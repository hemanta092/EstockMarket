import React from 'react';
import './App.css';
import { Routes, Route } from "react-router-dom";
import SignIn from './components/SignIn';
import StockView from './components/StockView';
import SignUp from './components/SignUp';
import RegisterCompany from './components/RegisterCompany';
import RegisterStock from './components/RegisterStock';

function App() {
  return (
    <Routes>
        {["/", "/signIn"].map(path => (<Route key={path} exact path={path} element={<SignIn />} />))}
        <Route exact path='/stockview' element={< StockView />}></Route>
        <Route exact path='/signUp' element={< SignUp />}></Route>
        <Route exact path='/registerCompany' element={< RegisterCompany />}></Route>
        <Route exact path='/registerStock' element={< RegisterStock />}></Route>  
    </Routes>
  );

}

export default App;
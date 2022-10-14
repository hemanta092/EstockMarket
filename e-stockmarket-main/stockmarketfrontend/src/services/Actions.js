import axios from "axios";


const REGISTER_URL = "http://stockmarket-1393091966.ap-south-1.elb.amazonaws.com/api/v1.0/market/command/user/registeruser";

const AUTH_URL = "http://stockmarket-1393091966.ap-south-1.elb.amazonaws.com/api/v1.0/market/query/user/authenticateuser";

const GET_STOCK_URL = "http://stockmarket-1393091966.ap-south-1.elb.amazonaws.com/api/v1.0/market/query/stock/info";

const GET_COMPNAY_NAMES = "http://stockmarket-1393091966.ap-south-1.elb.amazonaws.com/api/v1.0/market/query/company/getAllNames";

const REGISTER_COMP_URL = "http://stockmarket-1393091966.ap-south-1.elb.amazonaws.com/api/v1.0/market/command/company/register";

const REGISTER_STOCK_URL = "http://stockmarket-1393091966.ap-south-1.elb.amazonaws.com/api/v1.0/market/command/stock/registerstock";


export const registerUser = (userObject) => async (dispatch) => {
  dispatch(Request());
  try {
    const response = await axios.post(REGISTER_URL, userObject);
    (response.status === 200 && response.data !== 'undefined') ? sessionStorage.setItem('token', JSON.stringify(response.data)) : sessionStorage.setItem('token', null);
    dispatch(SaveSuccess(response.data));
    console.log(response.data);
    return response;
  } catch (error) {
    dispatch(Failure(error.message));
    return Promise.reject(error);
  }
};

export const authenticateUser = (userObject) => async (dispatch) => {
  dispatch(Request());
  try {
    const response = await axios.post(AUTH_URL, userObject);
    console.log(response);
    (response.status === 200 && response.data !== 'undefined') ? sessionStorage.setItem('token', JSON.stringify(response.data)) : sessionStorage.setItem('token', null);
    
    dispatch(SaveSuccess(response.data));
    return response;
  } catch (error) {
    dispatch(Failure(error.message));
    return Promise.reject(error);
  }
};


export const searchStocks = (searchObject) => async (dispatch) => {
  dispatch(Request());
  try {
    const response = await axios.post(GET_STOCK_URL, searchObject);
    dispatch(SearchSuccess(response.data));
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.log("stock search error",error);
    dispatch(Failure(error.message));
    return Promise.reject(error);
  }
};
  export const getCompanyNames = () => async dispatch =>  {
    try {
      const response = await axios.get(GET_COMPNAY_NAMES);
    console.log(response);
    dispatch(Request(response.data));
    return response.data;
     }
    catch (error) {
      dispatch(Failure(error.message));
      return Promise.reject(error);
    }

  }
export const registerCompany = (companyObject) => async (dispatch) => {
  dispatch(Request());
  try {
    const response = await axios.post(REGISTER_COMP_URL, companyObject);
    dispatch(SaveSuccess(response.data));
    console.log(response.data);
    return response.data;
  } catch (error) {
    dispatch(Failure(error.message));
    return Promise.reject(error);
  }
};

export const registerStock = (stockObject) => async (dispatch) => {
  dispatch(Request());
  try {
    const response = await axios.post(REGISTER_STOCK_URL, stockObject);
    dispatch(SaveSuccess(response.data));
    console.log(response.data);
    return response.data;
  } catch (error) {
    dispatch(Failure(error.message));
    return Promise.reject(error);
  }
};
const Request = () => {
  return {
    type: "REQUEST",
  };
};

const SaveSuccess = (object) => {
  return {
    type: "SAVE_SUCCESS",
    payload: object,
  };
};

const Failure = (error) => {
  return {
    type: "FAILURE",
    payload: error,
  };
};
  const SearchSuccess = (stock) => {
    return {
      type: "SEARCH_SUCCESS",
      payload: stock,
    };
  };
  
  
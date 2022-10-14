const initialState = {
    objects: [],
    error: "",
  };
  
  const reducer = (state = initialState, action) => {
    switch (action.type) {
      case "REQUEST":
        return {
          ...state,
        };
      case "SAVE_SUCCESS":
        return {
          message: action.payload,
          error: "",
        };
      case "FAILURE":
        return {
          objects: [],
          error: action.payload,
        };
      default:
        return state;
    }
  };
  
  export default reducer;
  
import React from 'react';
import { useGoogleLogin } from '@react-oauth/google';
import axios from 'axios';
import { toast } from 'react-toastify';
import { useDispatch } from 'react-redux';
import google from '../../assets/images/google.svg';
import {
  setAccessToken,
  setLoginState,
  setNickname,
  setMemberID,
} from '../../redux/slice/LoginSlice';

const GoogleOauth = () => {
  const dispatch = useDispatch();
  const login = useGoogleLogin({
    scope: 'email',
    onSuccess: (tokenResponse) => {
      console.log(tokenResponse.access_token);
      axios
        .post(
          `${process.env.REACT_APP_BE_API_URL}/oauth/google`,
          {},
          {
            headers: {
              'Access-Control-Allow-Origin': `${process.env.REACT_APP_FE_HEADER_URL}`,
              'Authorization': tokenResponse.access_token,
              // ...headers,
            },
          }
        )
        .then((res) => {
          dispatch(setMemberID(res.headers.memberid));
          dispatch(setAccessToken(res.headers.authorization));
          axios
            .get(`${process.env.REACT_APP_BE_API_URL}/members/my_page/intro`, {
              headers: {
                'Access-Control-Allow-Origin': `${process.env.REACT_APP_FE_HEADER_URL}`,
                'Authorization': res.headers.authorization,
              },
            })
            .then((resp) => {
              dispatch(setNickname(resp.data.nickname));
              console.log(resp);
              dispatch(setLoginState(true));
              toast.success('로그인 성공');
              window.location.href = '/';
            });
        });
    },
    onError: () => {
      console.log('Login Failed');
    },
  });
  return (
    <div className=" w-20 h-8 mb-4 ">
      <button onClick={() => login()}>
        <img src={google} className="w-12"></img>
      </button>
    </div>
  );
};

export default GoogleOauth;

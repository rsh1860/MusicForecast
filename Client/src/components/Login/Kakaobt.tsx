import React, { useEffect } from 'react';
import axios from 'axios';
import { useDispatch } from 'react-redux';
import { toast } from 'react-toastify';
import {
  setAccessToken,
  setLoginState,
  setNickname,
  setMemberID,
} from '../../redux/slice/LoginSlice';
import Klogin from '../../assets/images/kakao.png';

const Kakaobt = () => {
  const dispatch = useDispatch();
  const REST_API_KEY = `${process.env.REACT_APP_KAKAO_AUTH_CLIENT_ID}`;
  const REDIRECT_URI = `${process.env.REACT_APP_REDIRECT_URL}`;

  const codeRequestURL = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;
  const handleLogin = () => {
    window.location.href = codeRequestURL;
  };

  useEffect(() => {
    const code = new URL(window.location.href).searchParams.get('code');
    console.log(code);
    const headers = {
      'Access-Control-Allow-Origin': `${process.env.REACT_APP_FE_HEADER_URL}`,
      'code': code,
    };
    axios
      .post(`${process.env.REACT_APP_BE_API_URL}/oauth/v2/kakao`, {}, { headers })
      .then((res) => {
        console.log(res.headers.authorization);
        dispatch(setAccessToken(res.headers.authorization));

        axios
          .get(`${process.env.REACT_APP_BE_API_URL}/members/my_page/intro`, {
            headers: {
              'Access-Control-Allow-Origin': `${process.env.REACT_APP_FE_HEADER_URL}`,
              'Authorization': res.headers.authorization,
            },
          })
          .then((resp) => {
            console.log(resp.data);
            dispatch(setLoginState(true));
            dispatch(setNickname(resp.data.nickname));
            dispatch(setMemberID(res.headers.memberid));
            toast.success('로그인 성공');
            window.location.href = '/';
          });
      })
      .catch((err) => console.log(err));
  }, []);
  return (
    <>
      <button onClick={handleLogin}>
        <img src={Klogin} alt="" />
      </button>
    </>
  );
};

export default Kakaobt;

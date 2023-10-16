import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import jwtDecode from 'jwt-decode';
import axios from 'axios';
import { useForm } from 'react-hook-form';
import { Link } from 'react-router-dom';
import { toast } from 'react-toastify';
import {
  setNickname,
  setAccessToken,
  setRefreshToken,
  setMemberID,
  setLoginState,
} from '../../redux/slice/LoginSlice';
import Logo from '../../assets/images/logo.png';
import Email from '../../assets/images/email.svg';
import Lock from '../../assets/images/lock.svg';
import { RootState } from '../../redux/store';
import GoogleOauth from './GoogleOauth';
import Kakaobt from './Kakaobt';

interface Formvalue {
  username: string;
  password: string;
}
interface DecodedToken {
  exp: number;
}

const LoginOn = () => {
  const dispatch = useDispatch();
  const accessToken = useSelector((state: RootState) => state.login.accessToken);
  const refreshToken = useSelector((state: RootState) => state.login.refreshToken);
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<Formvalue>();

  const headers = {
    'Access-Control-Allow-Origin': `${process.env.REACT_APP_FE_HEADER_URL}`,
  };

  const handleLogin = async (username: string, password: string) => {
    try {
      const response = await axios
        .post(`${process.env.REACT_APP_BE_API_URL}/auth/login`, { username, password }, { headers })
        .then((res) => {
          dispatch(setAccessToken(res.headers.authorization));
          dispatch(setRefreshToken(res.headers.refreshtoken));
          dispatch(setMemberID(res.headers.memberid));
          dispatch(setLoginState(true));
          console.log(res.headers.authorization);

          axios
            .get(`${process.env.REACT_APP_BE_API_URL}/members/my_page/intro`, {
              headers: {
                'Access-Control-Allow-Origin': `${process.env.REACT_APP_FE_HEADER_URL}`,
                'Authorization': res.headers.authorization,
              },
            })
            .then((resp) => {
              console.log(resp.data);
              console.log(resp.data.nickname);
              dispatch(setNickname(resp.data.nickname));
            });
        });

      console.log(response);
      toast.success('로그인 성공');
      window.location.href = '/';
    } catch (error) {
      console.error('로그인 실패:', error);
      toast.error('아이디와 비밀번호가 일치하지 않습니다.');
    }
  };

  useEffect(() => {
    const refreshAccessToken = async () => {
      try {
        if (!refreshToken) {
          return;
        }
        const response = await axios.post(`${process.env.REACT_APP_BE_API_URL}`, { refreshToken });
        const newAccessToken = response.data.accessToken;

        localStorage.setItem('accessToken', newAccessToken);

        setAccessToken(newAccessToken);
      } catch (error) {
        console.error('Access Token 갱신 실패:', error);
      }
    };

    const checkAccessTokenExpiration = () => {
      if (!accessToken) {
        return;
      }
      const decodedToken: DecodedToken = jwtDecode(accessToken);
      const currentTime = Date.now() / 1000;
      if (decodedToken.exp <= currentTime) {
        refreshAccessToken();
      }
    };

    // 주기적으로 Access Token 유효성 확인 및 갱신
    const interval = setInterval(() => {
      checkAccessTokenExpiration();
    }, 60000);

    return () => {
      clearInterval(interval);
    };
  }, [accessToken, refreshToken]);

  const onSubmit = async (data: Formvalue) => {
    handleLogin(data.username, data.password);
  };

  return (
    <>
      <main className="bg-[#F2F2F2] h-screen">
        <div className=" flex flex-col justify-center items-center ">
          <Link to="/">
            <img src={Logo} alt="" className="my-20 hover:opacity-60" />
          </Link>
          <div className="flex flex-col border-2 border-solid border-none shadow-2xl rounded-2xl">
            <form
              onSubmit={handleSubmit(onSubmit)}
              className="flex flex-col w-[500px] h-[350px] ml-52 mt-20"
            >
              <div className="flex flex-row items-center text-xl">
                <img src={Email} alt="" className="w-10" />
                <label htmlFor="username">이메일</label>
              </div>
              <input
                type="text"
                {...register('username', {
                  pattern: {
                    value:
                      /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i,
                    message: '이메일 형식에 맞지 않습니다.',
                  },
                })}
                className={`w-[280px] h-8 border-2 border-solid border-white shadow-lg ${
                  errors.password ? 'border-red-500' : ''
                }`}
              />

              <div className="flex flex-row items-center text-xl mt-6">
                <img src={Lock} alt="" className="w-10" />
                <label htmlFor="password">비밀번호</label>
              </div>
              <input
                type="password"
                {...register('password', {
                  pattern: {
                    value: /^(?=.*[a-zA-Z])(?=.*[0-9]).{8,25}$/,
                    message: '비밀번호는 영문 숫자 8자리 이상이여야 합니다.',
                  },
                })}
                className={`w-[280px] h-8 border-2 border-solid border-white shadow-lg ${
                  errors.password?.message ? 'border-red-500' : ''
                }`}
              />
              <div className="flex flex-row justify-between w-[275px] mt-6">
                <div>
                  <Link to="/idfind">아이디 찾기</Link>
                </div>
                <div>
                  <Link to="/pwdfind">비밀번호 찾기</Link>
                </div>
                <div>
                  <Link to="/signup">회원가입</Link>
                </div>
              </div>
              <button
                disabled={isSubmitting}
                className="ml-2 mt-12 bg-[#C487F4] w-[250px] h-10 rounded-xl hover:bg-opacity-90 hover:bg-[#C487F4]"
              >
                로그인
              </button>
            </form>
            <div className="flex flex-row justify-between ml-60 mt-5 w-52 mb-24">
              <GoogleOauth />
              <Kakaobt />
            </div>
          </div>
        </div>
      </main>
    </>
  );
};

export default LoginOn;

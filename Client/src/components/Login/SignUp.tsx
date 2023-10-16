import React from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import axios from 'axios';
import Logo from '../../assets/images/logo.png';
import Email from '../../assets/images/email.svg';
import Lock from '../../assets/images/lock.svg';
import Person from '../../assets/images/person.svg';
import Calendar from '../../assets/images/calendar.svg';

const SignUp = () => {
  const headers = {
    'Access-Control-Allow-Origin': `${process.env.REACT_APP_FE_HEADER_URL}`,
  };

  interface IFormInput {
    email: string;
    password: string;
    nickname: string;
    birthdate: string;
    questionNumber: string;
    auth_answer: string;
  }

  const history = useNavigate();
  const {
    handleSubmit,
    register,
    formState: { errors, isSubmitting },
  } = useForm<IFormInput>();

  const onSubmit = async (data: IFormInput) => {
    data.birthdate = data.birthdate.replace(/-/g, '');

    try {
      const response = await axios.post(
        `${process.env.REACT_APP_BE_API_URL}/members/signup`,
        data,
        { headers }
      );

      if (response.status === 201) {
        history('/login');
      } else {
        console.error('회원가입 실패');
      }
    } catch (error) {
      console.error('오류 발생:', error);
      alert('회원가입에 실패했습니다.');
    }
  };

  return (
    <>
      <main className="bg-[#F2F2F2] h-screen">
        <div className=" flex flex-col justify-center items-center ">
          <Link to="/">
            <img src={Logo} alt="" className="my-14 hover:opacity-60" />
          </Link>
          <div className="flex flex-col border-2 border-solid border-none shadow-2xl rounded-2xl">
            <div className="text-center font-spoqa-han-san-neo ">
              Music Forecast에 오신걸 환영합니다!
            </div>
            <form
              onSubmit={handleSubmit(onSubmit)}
              className="flex flex-col w-[550px] h-[700px] ml-48 mt-6"
            >
              <div className="flex flex-row items-center text-xl">
                <img src={Email} alt="" className="w-10" />
                <label htmlFor="email">이메일</label>
              </div>
              <div className="flex flex-row justify-between">
                <input
                  type="text"
                  {...register('email', {
                    pattern: {
                      value:
                        /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i,
                      message: '이메일 형식에 맞지 않습니다.',
                    },
                  })}
                  className={`w-[330px] h-8 border-2 border-solid border-white shadow-lg ${
                    errors.password ? 'border-red-500' : ''
                  }`}
                />
              </div>
              {errors.email && <span className="text-red-500">{errors.email.message}</span>}

              <div className="flex flex-row items-center text-xl mt-6">
                <img src={Lock} alt="" className="w-10" />
                <label htmlFor="password">비밀번호</label>
              </div>
              <input
                type="password"
                {...register('password', {
                  pattern: {
                    value: /^(?=.*[a-zA-Z])(?=.*[0-9]).{8,25}$/,
                    message: '비밀번호는 영문 숫자 8자리 이상과 특수문자 1개이상 포함해야 합니다.',
                  },
                })}
                className={`w-[330px] h-8 border-2 border-solid border-white shadow-lg ${
                  errors.password?.message ? 'border-red-500' : ''
                }`}
              />
              {errors.password && <span className="text-red-500">{errors.password.message}</span>}

              <div className="flex flex-row items-center text-xl mt-4">
                <img src={Person} alt="" className="w-10" />
                <label htmlFor="name">닉네임</label>
              </div>
              <input
                type="text"
                id="nickname"
                {...register('nickname', { required: '닉네임은 필수 입니다.' })}
                className="w-[330px] h-8 border-2 border-solid border-white shadow-lg"
              />
              {errors.nickname && <span className="text-red-500">{errors.nickname.message}</span>}

              <div className="flex flex-row items-center text-xl mt-4">
                <img src={Calendar} alt="" className="w-10" />
                <label htmlFor="birthdate">생년월일</label>
              </div>
              <input
                type="date"
                className={`w-[330px] rounded-md ${errors.birthdate ? 'border-red-500' : ''}`}
                {...register('birthdate', { required: '생년월일은 필수 입니다.' })}
              />
              {errors.birthdate && <span className="text-red-500">{errors.birthdate.message}</span>}

              <div className="flex flex-col items-baseline mt-8">
                <div className="text-xl">비밀번호 찾기 질문</div>
                <select
                  {...register('questionNumber', { required: true })}
                  className="w-[330px] h-8"
                >
                  <option value=""></option>
                  <option value="1">가장 좋아하는 음식이 무엇인가요?</option>
                  <option value="2">가장 좋아하는 노래는?</option>
                  <option value="3">가장 여행하고 싶은 나라는?</option>
                </select>
                {errors.questionNumber && <span className="text-red-500">필수 항목입니다.</span>}
              </div>

              <div className="flex flex-col items-baseline mt-8">
                <div className="text-xl">비밀번호 찾기 입력</div>
                <input
                  type="text"
                  id="auth_answer"
                  {...register('auth_answer', { required: '비밀번호 찾기 입력은 필수 입니다.' })}
                  className={`w-[330px] h-8 ${errors.birthdate ? 'border-red-500' : ''}`}
                ></input>
                {errors.auth_answer && (
                  <span className="text-red-500">{errors.auth_answer.message}</span>
                )}
              </div>
              <button
                type="submit"
                disabled={isSubmitting}
                className="ml-10 mt-12 bg-[#C487F4] w-[250px] h-10 rounded-xl hover:bg-opacity-90 hover:bg-[#C487F4]"
              >
                회원가입
              </button>
            </form>
          </div>
        </div>
      </main>
    </>
  );
};

export default SignUp;

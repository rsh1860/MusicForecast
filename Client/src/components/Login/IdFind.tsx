import React from 'react';
import axios from 'axios';
import { useForm } from 'react-hook-form';
import { Link } from 'react-router-dom';
import Logo from '../../assets/images/logo.png';
import Calendar from '../../assets/images/calendar.svg';

interface IdInput {
  nickname: string;
  birthdate: string;
  questionNumber: string;
  auth_answer: string;
}

const IdFind = () => {
  const headers = {
    'Access-Control-Allow-Origin': `${process.env.REACT_APP_FE_HEADER_URL}`,
  };
  const {
    handleSubmit,
    register,
    formState: { errors, isSubmitting },
  } = useForm<IdInput>();

  const onsubmit = async (data: IdInput) => {
    data.birthdate = data.birthdate.replace(/-/g, '');

    const url = `${process.env.REACT_APP_BE_API_URL}/find/username`;
    try {
      const response = await axios.get(url, {
        params: data,
        headers,
      });

      if (response.status === 200) {
        alert(`회원님의 아이디는 ${response.data.email} 입니다.`);
        window.location.href = '/login';
      }
    } catch (error) {
      console.error('오류 발생:', error);
    }
  };
  return (
    <>
      <div className="bg-[#F2F2F2] h-screen">
        <div className=" flex flex-col justify-center items-center ">
          <Link to="/">
            <img src={Logo} alt="" className="my-20" />
          </Link>
          <div className="flex flex-col items-center border-2 border-solid border-none shadow-2xl rounded-2xl w-[600px] h-[650px]">
            <form onSubmit={handleSubmit(onsubmit)} className="flex flex-col mt-12">
              <div className="text-2xl">아이디 찾기</div>
              <div className="text-xl mt-10">닉네임</div>
              <input
                className="w-[330px] h-8 rounded-lg"
                placeholder="회원가입시 설정한 닉네임을 입력해주세요."
                type="text"
                id="nickname"
                {...register('nickname', { required: '아이디는 뭘로 찾아야 하나요?' })}
              ></input>
              {errors.nickname && <p style={{ color: 'red' }}>{errors.nickname.message} </p>}
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
                <div className="text-xl">회원가입시 설정한 질문</div>
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
                <div className="text-xl">질문에 대한 답변 입력</div>
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
                확인
              </button>
            </form>
          </div>
        </div>
      </div>
    </>
  );
};

export default IdFind;

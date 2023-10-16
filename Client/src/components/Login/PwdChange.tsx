import React from 'react';
import axios from 'axios';
import { useSelector } from 'react-redux';
import { useForm } from 'react-hook-form';
import { Link } from 'react-router-dom';
import { RootState } from '../../redux/store';
import Logo from '../../assets/images/logo.png';

interface PwchangInput {
  password: string;
  memberId: number;
}

const PwdChange = () => {
  const memberId = useSelector((state: RootState) => state.login.memberid);
  const headers = {
    'Access-Control-Allow-Origin': `${process.env.REACT_APP_FE_HEADER_URL}`,
  };
  const {
    handleSubmit,
    register,
    formState: { errors, isSubmitting },
  } = useForm<PwchangInput>();

  const onsubmit = async (data: PwchangInput) => {
    data.memberId = memberId;
    const url = `${process.env.REACT_APP_BE_API_URL}/find/pw`;
    try {
      const response = await axios.patch(url, {
        params: data,
        headers,
      });

      if (response.status === 200) {
        console.log('비밀번호 변경 성공');
        console.log(response.data);
        alert('비밀번호가 변경되었습니다.');
        window.location.href = '/login';
      }
    } catch (error) {
      console.error('오류 발생:', error);
      console.log(url);
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
              <div className="text-2xl">비밀번호 수정</div>
              <div className="flex flex-row items-center text-xl mt-6">
                <label htmlFor="password">새로운 비밀번호 입력</label>
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

export default PwdChange;

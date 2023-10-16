import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import 'animate.css';
import { useSelector } from 'react-redux';
import { RootState } from '../redux/store';

import Header from '../components/Header';

const EditProfile = () => {
  // const memberid = useSelector((state: RootState) => state.login.memberid);
  const accessToken = useSelector((state: RootState) => state.login.accessToken);
  const [nickname, setNickname] = useState('');
  const [myintro, setMyintro] = useState('');
  const navigate = useNavigate();

  const headers = {
    'Access-Control-Allow-Origin': `${process.env.REACT_APP_FE_HEADER_URL}`,
    'Authorization': accessToken,
  };

  const handleEdit = () => {
    axios
      .patch(
        `${process.env.REACT_APP_BE_API_URL}/my_page/101`,
        {
          image: '',
          intro: myintro,
          nickname,
        },
        { headers }
      )
      .then((res) => {
        console.log(res);
        navigate('../');
      })
      .catch((err) => {
        console.log(err);
      });
  };
  return (
    <>
      <div>
        <Header />
        <main className="bg-[#F2F2F2] h-screen">
          <div className=" flex flex-col justify-center items-center ">
            <div className="flex flex-col border-2 border-solid border-none shadow-2xl rounded-2xl mt-20 items-center justify-center">
              <form className="flex flex-col w-[800px] h-[700px] justify-center items-center">
                <div className="flex flex-row items-center text-xl">
                  <label htmlFor="username">닉네임</label>
                </div>
                <input
                  className="w-[275px] h-[50px] text-center"
                  value={nickname}
                  onChange={(e) => setNickname(e.target.value)}
                />

                <div className="flex flex-row items-center text-xl mt-6">
                  <label htmlFor="password">자기소개</label>
                </div>
                <textarea
                  className="w-[600px] h-[400px] py-5 px-5"
                  value={myintro}
                  onChange={(e) => setMyintro(e.target.value)}
                />
                <div className="flex flex-row justify-between w-[275px] mt-6"></div>

                <button
                  onClick={handleEdit}
                  className="ml-2 mt-12 bg-[#C487F4] w-[250px] h-10 rounded-xl hover:bg-opacity-90 hover:bg-[#C487F4]"
                >
                  확인
                </button>
              </form>
            </div>
          </div>
        </main>
      </div>
    </>
  );
};

export default EditProfile;

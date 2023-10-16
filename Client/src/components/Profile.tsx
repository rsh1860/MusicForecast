import { useNavigate } from 'react-router-dom';
import { UserIntro } from '../pages/Mypage';
// import { useEffect, useState } from 'react';
import userphoto from '../assets/images/user.png';
import 'animate.css';

export type Profileprops = {
  userIntro: UserIntro;
  // setUserIntro: any;
};

const Profile = ({ userIntro }: Profileprops) => {
  const navigate = useNavigate();
  return (
    <>
      <div className="flex flex-col w-full items-center">
        <div className="flex h-[150px] w-[875px] items-center justify-center border-b-2 border-gray-500">
          <div className="flex w-[150px] h-[150px] justify-center items-center ">
            <img className="w-[130px] h-[130px] " src={userphoto} alt="유저프로필" />
          </div>
          <div className="flex w-[600px] items-center justify-between">
            <span className="w-40vw ml-4">{userIntro.nickname}</span>
            <div>
              <button
                className="w-[150px] h-[30px] bg-white shadow-md hover:shadow-lg rounded-md"
                onClick={() => navigate('./edit')}
              >
                개인정보 수정
              </button>
            </div>
          </div>
        </div>
        <div className="w-[875px] h-[120px] border-t-2 border-[#363636] border-solid">
          {userIntro.intro}
        </div>
      </div>
    </>
  );
};

export default Profile;

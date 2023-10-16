import React from 'react';
import Headers from '../Header';

const Contents = () => {
  return (
    <>
      <div className=" bg-gradient-to-b h-screen from-[#d8e6ef] to-[#87c4ed]  ">
        <Headers />
        <header className="w-[1140] bg-center flex ">
          <img
            src="https://cdn.discordapp.com/attachments/1123861236124229664/1151401600435372032/Weather_Forecast_Mobile_App_Concept-removebg-preview.png"
            alt=""
            className="w-[750px] h-[600px] mt-20 ml-30 transform transition-transform hover:scale-x-[-1] hover:transition-delay-2000 "
          />
          <div className="w-[1140]  flex flex-col justify-center ml-32 ">
            <h2 className="text-7xl h-40 font-['Anton-Regular'] mt-30 ">🎶 MUSIC FORECAST </h2>
            <div className="flex flex-col text-4xl font-bold font-spoqa-hansans w-[750px]">
              <p className="text-2xl mt-4">
                {' '}
                Music Forecast는 날씨 예보와 음악을 접목시켜 여러분의 일상에 더 많은 즐거움을
                더해주는 웹 사이트입니다.{' '}
              </p>
              <p className="text-2xl mt-4">
                날씨, 운동, 기분 및 사용자의 취향에 맞게 최적의 음악을 추천하고, 사용자들끼리 음악을
                공유하며 연결하는 웹 사이트를 기획하고, 제작했습니다.{' '}
                <p className="text-4xl font-bold mt-4">지금 MUSIC FORECAST와 함께하세요 !</p>{' '}
              </p>
            </div>
          </div>
        </header>
      </div>
    </>
  );
};

export default Contents;

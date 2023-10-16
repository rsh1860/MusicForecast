import React from 'react';

// import { Navigation, Pagination, Scrollbar, Mousewheel, Keyboard } from 'swiper';
// import { Swiper, SwiperSlide } from 'swiper/react';

import Songfind from './Songfind';
import Contents from './Contents';
import Footer from './Footer';
import Wrapper from '../Wrapper/Wrapper';
import MbtiMain from './MbtiMain';

const Main = () => {
  // const slides = [<Contents />, <Chart />, <Footer />];
  return (
    <div className="w-full bg-gradient-to-b from-[#D5E5F0] to-[#87c4ed] relative overflow-hidden">
      {/* <Swiper
        modules={[Navigation, Pagination, Scrollbar, Mousewheel, Keyboard]}
        style={{ height: '100vh' }}
        keyboard={true}
        direction="vertical"
        slidesPerView={1}
        speed={2500}
        mousewheel={{ sensitivity: 0.5, thresholdDelta: 120 }}
        navigation
        pagination={{ clickable: true }}
        scrollbar={{ draggable: true, verticalClass: 'swiper-scrollbar-vertical' }}
      >
        {slides.map((slide, index) => (
          <SwiperSlide key={index}>{slide}</SwiperSlide>
        ))}
      </Swiper> */}
      <Wrapper>
        <Contents />
      </Wrapper>
      <Songfind />,
      <MbtiMain />
      <Footer />
    </div>
  );
};

export default Main;

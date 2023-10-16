import React, { useState, useEffect, useRef } from 'react';
import { useScroll, motion } from 'framer-motion';
import { Link } from 'react-router-dom';

const Songfind = () => {
  const ref = useRef<HTMLDivElement>(null);
  const { scrollYProgress } = useScroll({
    target: ref,
    offset: ['start end', 'end end'],
  });

  const [isVisible, setIsVisible] = useState<boolean>(false);

  useEffect(() => {
    const handleScroll = () => {
      if (scrollYProgress.get() > 0.9) {
        setIsVisible(true);
      } else if (scrollYProgress.get() < 0.9) {
        setIsVisible(false);
      }
    };

    window.addEventListener('scroll', handleScroll);

    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  }, [scrollYProgress]);

  const textMotion = {
    initial: { opacity: 0 },
    animate: { opacity: isVisible ? 1 : 0, y: isVisible ? 0 : -50 },
    exit: { opacity: 0, y: isVisible ? -50 : 0 },
    transition: { duration: 1, ease: 'easeInOut' },
  };

  const imageMotion = {
    initial: { opacity: 0 },
    animate: { opacity: isVisible ? 1 : 0, y: isVisible ? 1 : -75 },
    exit: { opacity: 0, y: isVisible ? -50 : 0 },
    transition: { duration: 1, ease: 'easeInOut' },
  };

  return (
    <>
      <header className="bg-cover bg-center h-screen bg-[#71b8f6]-500" style={{}}>
        <div className="w-full h-full  bg-opacity-70">
          <div className="flex flex-col justify-center items-center font-spoqa-hansans font-bold ">
            <motion.div ref={ref} {...textMotion}>
              <div className="   text-5xl mt-20 ">
                <p>당신이 어디에 있든</p>
                <p>당신의 기분에 따라</p>
                <span>날씨에 따라</span>
                <p>
                  <p className="text-[#86abe0]">자기의</p> 노래를 추천하는
                </p>
              </div>
            </motion.div>
            <motion.div {...imageMotion}>
              <img
                src="https://i.pinimg.com/originals/84/b9/78/84b978023e61d0787c1b7f9af335ae23.gif"
                alt=""
                className="w-[350px] h-[300px] my-24 "
              />
              <Link to="/weatherRecommend">
                <div className="w-[200px] h-12 bg-[#86abe0] font-spoqa-han-san-neo text-2xl ml-16 rounded-lg  flex items-center justify-center hover:opacity-90">
                  노래 찾으러 가기
                </div>
              </Link>
            </motion.div>
          </div>
        </div>
      </header>
    </>
  );
};
export default Songfind;

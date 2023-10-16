import React, { useState, useEffect, useRef } from 'react';
import { useScroll, motion } from 'framer-motion';
import { Link } from 'react-router-dom';
// import Player from '../../assets/images/player1.png';

const MbtiMain = () => {
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

  const imageMotion = {
    initial: { opacity: 0 },
    animate: { opacity: isVisible ? 1 : 0, x: isVisible ? 1 : -95 },
    exit: { opacity: 0, x: isVisible ? -10 : 0 },
    transition: { duration: 1, ease: 'easeInOut' },
  };
  return (
    <>
      <div className="h-screen bg-gradient-to-b from-[#D5E5F0] to-[#87c4ed] font-spoqa-hansans font-medium flex items-center justify-center">
        <motion.div ref={ref} {...imageMotion}>
          <img
            src="https://i.pinimg.com/originals/5c/0f/68/5c0f6834e0f766121ee18a619243debc.gif"
            alt=""
            className="w-[400px] h-[400px] "
          />
        </motion.div>
        <div className="ml-32 flex flex-col">
          <h2 className="text-5xl ">자기만의 노래를 찾기 힘들때!</h2>
          <h2 className="text-5xl mb-12">MUBTI를 통해 간편하게 찾아보세요!</h2>
          <span className="text-2xl">여러분의 선택으로 노래를 찾아보세요!</span>
          <Link to="/mubti">
            <button className="w-40 h-12 rounded-lg bg-[#94B0FC] hover:opacity-70 my-20">
              MUBTI 알아보러가기
            </button>
          </Link>
        </div>
      </div>
    </>
  );
};

export default MbtiMain;

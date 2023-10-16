import React, { ReactNode } from 'react';
import { motion, Variants } from 'framer-motion';
import { pageEffect } from '../Styles/animation';

interface WrapperProps {
  children: ReactNode;
  // 다른 props를 필요에 따라 추가할 수 있습니다.
}

const Wrapper: React.FunctionComponent<WrapperProps> = ({ children, ...rest }) => {
  return (
    <motion.div
      initial="initial"
      animate="in"
      exit="out"
      transition={{ duration: 0.5 }}
      variants={pageEffect as Variants}
      {...rest}
    >
      {children}
    </motion.div>
  );
};

export default Wrapper;

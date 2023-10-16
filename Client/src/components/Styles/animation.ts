export const pageEffect = {
  initial: { opacity: 0, transform: 'translateX(-100%)' },
  in: {
    opacity: 1,
    transform: 'translateX(0%)', // 이 부분을 수정하여 원하는 위치로 이동
    transition: {
      type: 'spring', // spring 효과 사용
      damping: 10, // 감쇠 설정
      stiffness: 50, // 강성 설정
      duration: 0.5, // 0.5초 동안 애니메이션 진행
    },
  },
  out: {
    opacity: 0,
    transform: 'translateX(-50%)', // 이 부분을 수정하여 원하는 위치로 이동
    transition: {
      type: 'spring', // spring 효과 사용
      damping: 100, // 감쇠 설정
      stiffness: 50, // 강성 설정
      duration: 0.5, // 0.5초 동안 애니메이션 진행
    },
  },
};

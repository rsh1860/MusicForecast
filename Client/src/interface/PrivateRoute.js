import React from 'react';
import { Navigate } from 'react-router-dom';
import { toast } from 'react-toastify';

const PrivateRoute = ({ authenticated, component: Component }) => {
  if (!authenticated) {
    // 사용자가 인증되지 않았을 때 Toast 메시지를 표시합니다.
    toast.error('로그인이 필요합니다.');
    return <Navigate to="/login" replace />;
  }
  return <Component />;
};
export default PrivateRoute;

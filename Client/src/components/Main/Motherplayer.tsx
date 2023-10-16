import React from 'react';
import { useState } from 'react';
import data from './Data/data.json';
import Player from './Plyer';

const Motherplayer = () => {
  const [currentIdx, setCurrentIdx] = useState(0);
  const [currentUrl, setCurrentUrl] = useState(data[currentIdx].url);

  const [isPlaying, setIsPlaying] = useState<boolean>(false);
  const [isMuted, setIsMuted] = useState<boolean>(false);
  const [currentTime, setCurrentTime] = useState<number>(0);
  const [duration, setDuration] = useState<number | null>(null);

  return (
    <div>
      <Player
        currentUrl={currentUrl}
        setCurrentUrl={setCurrentUrl}
        duration={duration}
        setDuration={setDuration}
        isPlaying={isPlaying}
        setIsPlaying={setIsPlaying}
        isMuted={isMuted}
        setIsMuted={setIsMuted}
        currentTime={currentTime}
        setCurrentTime={setCurrentTime}
        url={`${currentUrl}`}
        currentIdx={currentIdx}
        setCurrentIdx={setCurrentIdx}
      />
    </div>
  );
};

export default Motherplayer;

import { useState } from 'react';

const useModal = (initialState = false) => {
  const [isOpen, setIsOpen] = useState(initialState);
  const [data, setData] = useState(null);

  const open = (modalData = null) => {
    setData(modalData);
    setIsOpen(true);
  };

  const close = () => {
    setIsOpen(false);
    setData(null);
  };

  const toggle = () => {
    setIsOpen((prev) => !prev);
  };

  return {
    isOpen,
    data,
    open,
    close,
    toggle,
    setData,
  };
};

export default useModal;

import { useState, useEffect, useCallback } from 'react';
import { message } from 'antd';

const useApi = (apiFunction, immediate = true) => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const execute = useCallback(
    async (...params) => {
      try {
        setLoading(true);
        setError(null);
        const result = await apiFunction(...params);
        setData(result);
        return result;
      } catch (err) {
        const errorMessage = err.response?.data?.message || err.message || 'Erro ao processar requisição';
        setError(errorMessage);
        message.error(errorMessage);
        throw err;
      } finally {
        setLoading(false);
      }
    },
    [apiFunction]
  );

  useEffect(() => {
    if (immediate) {
      execute();
    }
  }, []);

  return {
    data,
    loading,
    error,
    execute,
    setData,
  };
};

export default useApi;

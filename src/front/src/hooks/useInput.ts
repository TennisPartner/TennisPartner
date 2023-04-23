import { useState, ChangeEvent } from "react";

type UseInputType = [
  number,
  (e: ChangeEvent<HTMLInputElement>) => void,
  () => void
];

const useInput = (initialValue: number): UseInputType => {
  const [value, setValue] = useState<number>(initialValue);

  const handleChange = (event: ChangeEvent<HTMLInputElement>): void => {
    setValue(+event.target.value);
  };

  const reset = (): void => {
    setValue(0);
  };

  return [value, handleChange, reset];
};

export default useInput;

import styled from 'styled-components';
import { colors } from './styles.js';

export const ResultContainer = styled.div`
  text-align: center;
  background: ${colors.bgMid};
  padding: 1.5rem;
  border-radius: 0.75rem;
`;

export const ResultTitle = styled.h1`
  font-size: clamp(1.5rem, 4vw, 2rem);
  font-weight: 700;
  margin-bottom: 1rem;
  color: ${({ isSuccess }) => (isSuccess ? colors.green500 : colors.red500)};
`;

export const ResultText = styled.p`
  color: #d1d5db;
  margin-bottom: 1.5rem;
`;
